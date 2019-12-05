package cn.yudianxx.admin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyongwen
 * @date 2019/12/5
 * @Description
 */
public class TKV {
    public static void main(String[] args) {
//        Animal dog = new Dog(); //dog拥有aniaml的属性
        Dog dog1 = new Dog();
//        Animal animal = new Animal();
        new TKV().instanceAnimal(dog1);
//        new TKV().instanceAnimal(dog);
//        new TKV().instanceAnimal(animal);
//
//        new TKV().instanceDog(dog1);
        List<Dog> dogs = new ArrayList<>();
        // 不会报错
        countLegs( dogs );
//         报错
//        countLegs1(dogs);
    }
    //
    public void instanceAnimal(Animal animal){
        System.out.println("111");
    }

    public void instanceDog(Dog dog){

    }
    static int countLegs (List<? extends Animal > animals ) {
        int retVal = 0;
        for ( Animal animal : animals )
        {
            retVal += animal.countLegs();
        }
        System.out.println(retVal);
        return retVal;
    }

    static int countLegs1 (List<Animal > animals ){
        int retVal = 0;
        for ( Animal animal : animals )
        {
            retVal += animal.countLegs();
        }
        return retVal;
    }
}

class Animal {
    Animal() {
        System.out.println("Animal");
    }
    void climp(){

    }
    int countLegs(){
        return 4;
    }
}

class Dog extends Animal {
    Dog() {
        System.out.println("Dog");
    }
    void smell(){

    }

}
