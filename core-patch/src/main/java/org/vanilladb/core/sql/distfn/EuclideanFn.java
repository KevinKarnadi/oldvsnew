package org.vanilladb.core.sql.distfn;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;
import org.vanilladb.core.sql.VectorConstant;

public class EuclideanFn extends DistanceFn {
    final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

    public EuclideanFn(String fld) {
        super(fld);
    }

    @Override
    protected double calculateDistance(VectorConstant vec) {
        var vSum = FloatVector.zero(SPECIES);
        var upperBound = SPECIES.loopBound(this.query.dimension());

        float[] leftVectorVal = this.query.asJavaVal();
        float[] rightVectorVal = vec.asJavaVal();

        for (int i = 0; i < upperBound; i += SPECIES.length()) {
            FloatVector left = FloatVector.fromArray(SPECIES, leftVectorVal, i);
            FloatVector right = FloatVector.fromArray(SPECIES, rightVectorVal, i);
            FloatVector diff = left.sub(right);
            vSum = diff.mul(diff).add(vSum);
        }

        float dist = vSum.reduceLanes(VectorOperators.ADD);

        // remaining values
        for (int i = upperBound; i < this.query.dimension(); i++) {
            float diff = this.query.get(i) - vec.get(i);
            dist += diff * diff;
        }

        return Math.sqrt(dist);
    }
    
}
