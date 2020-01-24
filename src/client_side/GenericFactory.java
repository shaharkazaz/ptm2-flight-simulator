package client_side;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GenericFactory<Product> {
	
	private interface Creator<Product>{
		public Product create();
	}
	
	Map<String,Creator<Product>> map;
	
	public GenericFactory() {
		map=new HashMap<>();
	}
	
	public void insertProduct(String key, Class<? extends Product> product) {
        map.put(key, new Creator<Product>(){
            @Override
            public Product create() {
                    try {
                            return product.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                            e.printStackTrace();
                    }
                return null;
            }
        });
	}
	
	public Product createProduct(String key){
		return map.containsKey(key) ? map.get(key).create() : null;
	}
}
