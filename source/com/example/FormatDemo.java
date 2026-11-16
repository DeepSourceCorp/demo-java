package com.example;
import java.util.List;import java.util.ArrayList;
public class FormatDemo{
  private   int count=0 ;
    public FormatDemo( int start ){count=start;}
 public List<String> names(  String a,String b ){
        List<String> out=new ArrayList<>();
   if(a!=null){out.add(a);}else{out.add("none");}
      out.add( b );
  return out;}
public int next(){ return ++count ; }
}
