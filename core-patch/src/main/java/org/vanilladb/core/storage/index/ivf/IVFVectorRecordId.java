package org.vanilladb.core.storage.index.ivf;

import org.vanilladb.core.sql.*;

public class IVFVectorRecordId {
    VectorConstant vec;
    BigIntConstant blk;
    IntegerConstant rid;

    public IVFVectorRecordId(VectorConstant vec, BigIntConstant blk, IntegerConstant rid) {
        this.vec = vec;
        this.blk = blk;
        this.rid = rid;
    }
}
