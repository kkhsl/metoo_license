package com.abtnetworks.totems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class TotemsTopologySsoApplication {
    public TotemsTopologySsoApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(TotemsTopologySsoApplication.class, args);
    }


//        @Component
//        static class Runner implements CommandLineRunner {
//        @Autowired
//        private CustomTokenController customTokenController;
//
//        Runner() {
//        }
//
//        public void run(String... paramVarArgs) throws Exception {
//            try {
//                UmsUserSsoTokenEntity entity = new UmsUserSsoTokenEntity();
//                entity.setType("client_credentials");
//                entity.setTokenValiditySeconds("93312000");
//                this.customTokenController.insert(null, entity);
//            } catch (Exception e) {
//            }
//        }
//    }
}
