//package com.ecom.routes;
//
//import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
//import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.function.*;
//
//@Configuration
////this is the class where all the routing routes are specified
//public class Routes {
//    //this function is used to route the response from server
//    //here we are using functional end point programming model
//    //here we are using handlerfunction and http method for routing the url
//
////    @Bean
////    public RouterFunction<ServerResponse> OrderFunctionRoute() {
////        return GatewayRouterFunctions.route("OrderService")
////                .route(RequestPredicates.POST("/order/place"),
////                        HandlerFunctions.http("http://localhost:8081"))
////                .build();
////    }
//
//    @Bean
//    public RouterFunction<ServerResponse> ProductFunctionRoute() {
//        return GatewayRouterFunctions.route("product-service")  // logical route ID (optional)
//                .route(RequestPredicates.POST("/api/product"),
//                        HandlerFunctions.http("lb://product-service"))
//                .route(RequestPredicates.POST("/api/category"),
//                        HandlerFunctions.http("lb://product-service"))
//                .build();
//    }
//}




