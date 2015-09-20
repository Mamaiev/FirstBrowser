package sample;

/**
 * Created by Dacha on 18.09.2015.
 */
public class prostoClass {
    public static void main(String[] args) {
        String url = "http://s3.amazonaws.com/TimeScapes/images/stills/4k/big_sur.jpg";
        System.out.println(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
    }
}

