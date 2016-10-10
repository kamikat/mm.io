package moe.banana.mmio.model;

public interface DataSource<T> {

    /**
     * @return number of items in source
     */
    int getItemCount();

    /**
     * @param position of item to retrieve
     * @return object or null if item is not available (?)
     */
    T getItem(int position);

}
