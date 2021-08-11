import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
public class HigherOrderUtils {

    public interface NamedBiFunction<R,T,U> extends java.util.function.BiFunction<R,T,U>{
        String name();
    }

    public static NamedBiFunction<Double,Double,Double> add = new NamedBiFunction<Double,Double,Double>() {
        @Override
        public String name() {
            return "add";
        }
        @Override
        public Double apply(Double num1, Double num2) {
            return num1 + num2;
        }
    };

    public static NamedBiFunction<Double,Double,Double> subtract = new NamedBiFunction<Double,Double,Double>() {
        @Override
        public String name() {
            return "diff";
        }
        @Override
        public Double apply(Double num1, Double num2) {
            return num1 - num2;
        }
    };

    public static NamedBiFunction<Double,Double,Double> multiply = new NamedBiFunction<Double,Double,Double>() {
        @Override
        public String name() {
            return "mult";
        }
        @Override
        public Double apply(Double num1, Double num2) {
            return num1 * num2;
        }
    };

    public static NamedBiFunction<Double,Double,Double> divide = new NamedBiFunction<Double,Double,Double>() {
        @Override
        public String name() {
            return "div";
        }
        @Override
        public Double apply(Double num1, Double num2) {
            if(num2==0){
                throw new java.lang.ArithmeticException();
            }
            return num1 / num2;
        }
    };

    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions){
        for(int i=0; i<args.size()-1; i++){
            args.set(i+1, (T) bifunctions.get(i).apply(args.get(i), args.get(i+1)));
        }
        return args.get(args.size()-1);
    }
    public static void main(String[] args) {
    }
}
