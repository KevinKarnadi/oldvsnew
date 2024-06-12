package org.vanilladb.core.query.algebra.vector;

import org.vanilladb.core.query.algebra.Plan;
import org.vanilladb.core.query.algebra.Scan;
import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.VectorConstant;
import org.vanilladb.core.sql.distfn.DistanceFn;
import org.vanilladb.core.storage.tx.Transaction;

import java.util.PriorityQueue;

/**
 * The Scan class for ANN search. It only retains the "i_id" field.
 */
public class NearestNeighborScan implements Scan {

    private static final String FIELD_ID = "i_id";
    Scan s;
    DistanceFn distFn;
    Transaction tx;
    PriorityQueue<DistIDPair> pq;
    boolean isBeforeFirsted = false;
    DistIDPair current;

    public NearestNeighborScan(Scan s, DistanceFn distFn, Transaction tx) {
        this.s = s;
        this.tx = tx;
        this.distFn = distFn;
    }

    @Override
    public void beforeFirst() {
        s.beforeFirst();
        pq = new PriorityQueue<>((a, b) -> {
            if (a.dist - b.dist < 0)
                return -1;
            else if (a.dist - b.dist > 0)
                return 1;
            return 0;
        });

        String fld = distFn.fieldName();
        while (s.next()) {
            double dist = distFn.distance((VectorConstant) s.getVal(fld));
            pq.add(new DistIDPair(dist, s.getVal(FIELD_ID)));
        }
        isBeforeFirsted = true;
    }

    @Override
    public boolean next() {
        if (!isBeforeFirsted)
            throw new IllegalStateException();
        if (!pq.isEmpty()) {
            current = pq.poll();
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        s.close();
    }

    @Override
    public boolean hasField(String fldName) {
        return fldName.equals(FIELD_ID);
    }

    @Override
    public Constant getVal(String fldName) {
        if (fldName.equals(FIELD_ID)) {
            return current.id;
        }
        return null;
    }

    private static class DistIDPair {
        double dist;
        Constant id;
        DistIDPair(double dist, Constant id) {
            this.dist = dist;
            this.id = id;
        }
    }
}
