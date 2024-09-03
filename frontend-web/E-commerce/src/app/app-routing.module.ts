import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {AdminPageComponent} from "./pages/admin/admin-page.component";
import {CatalogPageComponent} from "./pages/user/catalog-page/catalog-page.component";
import {LogbookPageComponent} from "./pages/admin/logbook-page/logbook-page.component";
import {loggedInGuard} from "./route-guards/logged-in.guard";
import {ProductPageComponent} from "./pages/admin/product-page/product-page.component";
import {UserPageComponent} from "./pages/user/user-page.component";
import {ProductDetailPageComponent} from "./pages/user/product-detail-page/product-detail-page.component";
import {ShoppingCartPageComponent} from "./pages/user/shopping-cart-page/shopping-cart-page.component";
import {SuccesPageComponent} from "./pages/user/succes-page/succes-page.component";
import {ProductCreationPageComponent} from "./pages/admin/product-creation-page/product-creation-page.component";
import {WishListPageComponent} from "./pages/user/wish-list-page/wish-list-page.component";

const routes: Routes = [
  {path: "", component: LoginPageComponent},
  {
    path: "admin", component: AdminPageComponent, canActivate: [loggedInGuard],
    children: [
      {path: "", component: ProductPageComponent, canActivate: [loggedInGuard]},
      {path: "logbook", component: LogbookPageComponent, canActivate: [loggedInGuard]},
      {path: "product", component: ProductCreationPageComponent, canActivate: [loggedInGuard]},
    ],
  },
  {
    path: "user", component: UserPageComponent, canActivate: [loggedInGuard],
    children: [
      {path: "catalog", component: CatalogPageComponent, canActivate: [loggedInGuard]},
      {path: "product/:id", component: ProductDetailPageComponent, canActivate: [loggedInGuard]},
      {path: "shopping-cart", component: ShoppingCartPageComponent, canActivate: [loggedInGuard]},
      {path: "wish-list", component: WishListPageComponent, canActivate: [loggedInGuard]}
    ]
  },
  {path: "succes", component: SuccesPageComponent, canActivate: [loggedInGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
