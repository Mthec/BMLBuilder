package mod.wurmunlimited.bml;

public interface BMLFor<T> {
    BML apply(T t, BML b);
}
