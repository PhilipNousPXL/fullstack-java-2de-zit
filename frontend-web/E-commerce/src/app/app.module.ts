import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginPageComponent} from './pages/login-page/login-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AdminPageComponent} from './pages/admin/admin-page.component';
import {CatalogPageComponent} from './pages/user/catalog-page/catalog-page.component';
import {HttpClientModule} from "@angular/common/http";
import {ProductComponent} from './components/product/product.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatChipsModule} from "@angular/material/chips";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatInputModule} from "@angular/material/input";
import {MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {ReplacePipe} from './pipes/replace/replace.pipe';
import {LogbookPageComponent} from './pages/admin/logbook-page/logbook-page.component';
import {MatTableModule} from "@angular/material/table";
import {ProductPageComponent} from './pages/admin/product-page/product-page.component';
import {UserPageComponent} from './pages/user/user-page.component';
import {ProductDetailPageComponent} from './pages/user/product-detail-page/product-detail-page.component';
import {NgOptimizedImage} from "@angular/common";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {ShoppingCartPageComponent} from './pages/user/shopping-cart-page/shopping-cart-page.component';
import {SuccesPageComponent} from './pages/user/succes-page/succes-page.component';
import {ProductCreationPageComponent} from './pages/admin/product-creation-page/product-creation-page.component';
import {MatDialogModule} from "@angular/material/dialog";
import {WishListPageComponent} from './pages/user/wish-list-page/wish-list-page.component';
import {LimitPipe} from './pipes/limit/limit.pipe';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    AdminPageComponent,
    CatalogPageComponent,
    ProductComponent,
    ReplacePipe,
    LimitPipe,
    LogbookPageComponent,
    ProductPageComponent,
    UserPageComponent,
    ProductDetailPageComponent,
    ShoppingCartPageComponent,
    SuccesPageComponent,
    ProductCreationPageComponent,
    WishListPageComponent,
    LimitPipe,
  ],
  imports: [
    MatSnackBarModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatChipsModule,
    MatCardModule,
    MatChipsModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatSidenavModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatCheckboxModule,
    MatTableModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    MatDialogModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
