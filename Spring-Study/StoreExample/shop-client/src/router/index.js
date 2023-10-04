import { createWebHistory, createRouter } from "vue-router";
import ShopComponent from "@/components/content/shop/ShopBaseComponent.vue";
import ExchangeComponent from "@/components/content/exchange/ExchangeBaseComponent.vue";
import BuyDiamondComponent from "@/components/content/exchange/tabs/BuyDiamondComponent.vue";
import RefundDiamondComponent from "@/components/content/exchange/tabs/RefundDiamondComponent.vue";
import ExchangeHistoryComponent from "@/components/content/exchange/tabs/ExchangeHistoryComponent.vue";
import BuyItemComponent from "@/components/content/shop/tabs/BuyItemComponent.vue";
import RefundItemComponent from "@/components/content/shop/tabs/RefundItemComponent.vue";
import SaleHistoryComponent from "@/components/content/shop/tabs/SaleHistoryComponent.vue";



const routes = [
    {
        path: "/",
        redirect: "/exchange"
    },
    {
        path: "/shop",
        redirect: "/shop/buy",
        name: "ShopComponent",
        component: ShopComponent,
        children: [
            {
                path: "buy",
                name: "BuyItemComponent",
                component: BuyItemComponent,
            },
            {
                path: "refund",
                name: "RefundItemComponent",
                component: RefundItemComponent,
            },
            {
                path: "history",
                name: "SaleHistoryComponent",
                component: SaleHistoryComponent,
            },
        
        ]
    },
    {
        path: "/exchange",  // ExchangeComponent.vue로 이동할 Path
        redirect: "/exchange/buy",
        name: "ExchangeComponent",  // router name
        component: ExchangeComponent,  // Path로 이동될 Component
        children: [
            {
                path: "buy",
                name: "BuyDiamondComponent",
                component: BuyDiamondComponent,
            },
            {
                path: "refund",  
                name: "RefundDiamondComponent", 
                component: RefundDiamondComponent,
            },
            {
                path: "history",
                name: "ExchangeHistoryComponent",
                component: ExchangeHistoryComponent,
            },
        
        ]
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;