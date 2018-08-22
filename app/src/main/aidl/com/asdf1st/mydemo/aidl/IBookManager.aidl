// IBookManager.aidl
package com.asdf1st.mydemo.aidl;
import com.asdf1st.mydemo.aidl.Book;
import com.asdf1st.mydemo.aidl.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void addBook(in Book book);

    List<Book> getBookList();
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}
