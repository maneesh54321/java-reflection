import com.ms.reflection.Customer;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Customer customer = new Customer("Ram", 32, LocalDateTime.now().minusYears(32));

		Class<? extends Customer> aClass = customer.getClass();
		System.out.println("\n\n\n################# Class: " + aClass);

		System.out.println("\n\n\n################# Customer fields: ");
		System.out.println(Arrays.toString(aClass.getFields()));

		System.out.println("\n\n\n################# Customer declared fields");
		System.out.println(Arrays.toString(aClass.getDeclaredFields()));

		System.out.println("\n\n\n################# Customer methods");
		Arrays.stream(aClass.getMethods()).forEach(System.out::println);

		System.out.println("\n\n\n################# Customer declared methods");
		Arrays.stream(aClass.getDeclaredMethods()).forEach(System.out::println);

		System.out.println("\n\n\n################# Customer constructors");
		Arrays.stream(aClass.getConstructors()).forEach(System.out::println);

		System.out.println("\n\n\n################# Customer declared constructors");
		Arrays.stream(aClass.getDeclaredConstructors()).forEach(System.out::println);
	}
}