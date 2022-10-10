package com.example;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.models.Person;
import com.example.models.Product;

public class App {
    
    public static void main(String[] args) {
        Person p1 = new Person(1, "Mito", LocalDate.of(1991, 1, 21));
        Person p2 = new Person(2, "Code", LocalDate.of(1990, 2, 21));
        Person p3 = new Person(3, "Jaime", LocalDate.of(1980, 6, 23));
        Person p4 = new Person(4, "Duke", LocalDate.of(2019, 5, 15));
        Person p5 = new Person(5, "James", LocalDate.of(2010, 1, 4));

        Product pr1 = new Product(1, "Ceviche", 15.0);
        Product pr2 = new Product(2, "Chilaquiles", 25.50);
        Product pr3 = new Product(3, "Bandeja Paisa", 35.50);
        Product pr4 = new Product(4, "Ceviche", 15.0);

        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5);
        List<Product> products = Arrays.asList(pr1, pr2, pr3, pr4);

        /*
         * Lambda: funciones reusables dentro de una fracción de código
         */

        /*
         * Esta función permite recorrerla lista
         */
        persons.forEach(System.out::println);

        // Genera un filtro por año        
        List<Person> filteredList1 = persons.stream()
                                        .filter(p -> p.getBirthday().getYear() >= 2015)
                                        .collect(Collectors.toList());

        App.printList(filteredList1);        

        // Mapea una funcion a otra o a un tipo de dato
        Function<String, String> coderFunction = name -> "Coder " + name;
        List<String> filteredList2 = persons.stream()
                                        .map(Person::getName)
                                        .map(coderFunction)
                                        .collect(Collectors.toList());
        App.printList(filteredList2);      

        // Ordena y devuleve una lista
        Comparator<Person> byNameAsc = (Person o1, Person o2) -> o1.getName().compareTo(o2.getName());


        List<Person> filteredList3 = persons.stream()
                                        .sorted(byNameAsc)
                                        .collect(Collectors.toList());
        App.printList(filteredList3);          
        
        //Busca concordancias
        Predicate<Person> startsWithPredicate = person -> person.getName().startsWith("J");
        // anyMatch : No evalua todo el stream, termina en la coincidencia
        boolean rpta1 = persons.stream()
                                .anyMatch(startsWithPredicate);        
        // allMatch : Evalua todo el stream bajo la condicion
        boolean rpta2 = persons.stream()
                                .allMatch(startsWithPredicate);        
        
        // noneMatch : Evalua todo el stream bajo la condicion
        boolean rpta3 = persons.stream()
                                .noneMatch(startsWithPredicate);                
        
        // Paginación
        int pageNumber = 1;
        int pageSize = 2;
        List<Person> filteredList4 = persons.stream()
                                        .skip(pageNumber * pageSize)
                                        .limit(pageSize)
                                        .collect(Collectors.toList());
        App.printList(filteredList4);
        
        // Ordena por grupos y devuelve secciones de grupos un map
        Map<String, List<Product>> collect1 = products.stream()
                                                .filter(p -> p.getPrice() > 20)
                                                .collect(Collectors.groupingBy(Product::getName));
        System.out.println(collect1);
        
        // Cuenta por grupos
        Map<String, Long> collect2 = products.stream()
                                            .collect(Collectors.groupingBy(
                                                Product::getName, Collectors.counting()
                                                )
                                            );
        System.out.println(collect2);
        
        //Agrupando por nombre producto y sumando
        Map<String, Double> collect3 = products.stream()
                                            .collect(Collectors.groupingBy(
                                                Product::getName, 
                                                Collectors.summingDouble(Product::getPrice)
                                                )
                                            );
        System.out.println(collect3);
        
        //Obteniendo suma y resumen
        DoubleSummaryStatistics statistics = products.stream()
                                                .collect(Collectors.summarizingDouble(Product::getPrice));
        System.out.println(statistics);
        
        //obtiene el valor total de la lista obtenida    
        Optional<Double> sum = products.stream()
                                    .map(Product::getPrice)
                                    .reduce(Double::sum);
                                    //.reduce((a,b) -> a+b)
        System.out.println(sum.get());


    }

    public static void printList(List<?> list){
        list.forEach(System.out::println);
    }   
}
