package mouse.project.algorithm.sweep;



public interface Status<T> {
    Neighbors<T> insert(T t);
    Neighbors<T> delete(T segment);
}
