// IOnNewBookArrivedListener.aidl
package com.asdf1st.mydemo.aidl;
import com.asdf1st.mydemo.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
