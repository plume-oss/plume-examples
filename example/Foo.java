public class Foo {

    public static void main(String[] args) {
        var a = 1;
        taint(a);
        var b = 2;
        var c = Bar.add(a, b);
        System.out.println(c);
    }

    public static void taint(int u) {
        // Do something malicious here
    }

}