package snake;


public class sCls {
    
    public int random(int min, int max){
        max+=1;
        int r= (int) (Math.random()*(max-min))+min;
        
        String s=r+"";
        s=s.substring(0, s.length()-1);
        s=s+"0";
        r=Integer.parseInt(s);
        
        return r;
    }
    
    public void foodMove(){

    }

}
