package org.vanilladb.core.sql;

public class VectorConstantRange extends ConstantRange {
    Constant low;
    public VectorConstantRange(VectorConstant low) {
        this.low = low;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean hasLowerBound() {
        return true;
    }

    @Override
    public boolean hasUpperBound() {
        return true;
    }

    @Override
    public Constant low() {
        return low;
    }

    @Override
    public Constant high() {
        return low;
    }

    @Override
    public boolean isLowInclusive() {
        return true;
    }

    @Override
    public boolean isHighInclusive() {
        return true;
    }

    @Override
    public double length() {
        return 1;
    }

    @Override
    public ConstantRange applyLow(Constant c, boolean inclusive) {
        return null;
    }

    @Override
    public ConstantRange applyHigh(Constant c, boolean incl) {
        return null;
    }

    @Override
    public ConstantRange applyConstant(Constant c) {
        return null;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public Constant asConstant() {
        return low;
    }

    @Override
    public boolean contains(Constant c) {
        return c == low;
    }

    @Override
    public boolean lessThan(Constant c) {
        return c == low;
    }

    @Override
    public boolean largerThan(Constant c) {
        return c == low;
    }

    @Override
    public boolean isOverlapping(ConstantRange r) {
        return false;
    }

    @Override
    public boolean contains(ConstantRange r) {
        return false;
    }

    @Override
    public ConstantRange intersect(ConstantRange r) {
        return null;
    }

    @Override
    public ConstantRange union(ConstantRange r) {
        return null;
    }
}