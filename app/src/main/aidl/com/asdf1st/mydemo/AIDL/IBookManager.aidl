// IBookManager.aidl
package com.asdf1st.mydemo.AIDL;

// Declare any non-default types here with import statements

interface IBookManager {
     List<Book> getBookList();
     void addBook(in Book book);
}
