package DAO;

public interface VolleyCallback<T>{
   void onSuccess(T result);
   void onFail();
}