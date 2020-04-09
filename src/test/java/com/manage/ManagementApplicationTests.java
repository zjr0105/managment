package com.manage;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class ManagementApplicationTests {

    /*@Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }*/

/*
    public static void main(String[] args) {

    }*/

    public Object instance;

    public ManagementApplicationTests(String name){

    }

    public static void testGc(){
        ManagementApplicationTests tests = new ManagementApplicationTests("A");
        ManagementApplicationTests b   = new ManagementApplicationTests("b");

        tests.instance = b;
        b.instance = tests;



        tests = null;
        b = null;
    }



}
