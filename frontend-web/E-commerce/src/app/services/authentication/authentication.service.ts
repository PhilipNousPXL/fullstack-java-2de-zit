import {Injectable} from '@angular/core';

@Injectable({providedIn: 'root'})
export class AuthenticationService {

  private _token: string | undefined;

  get authToken(): string | undefined {
    return this._token;
  }

  isLoggedIn(): boolean {
    return this._token != undefined;
  }

  isAdmin(): boolean {
    return this._token?.endsWith("admin") || false;
  }

  logIn(userName: String, password: String) {
    const admins = ["philip", "mark", "paul"];
    if (admins.includes(userName.toLowerCase().trim())) {
      this._token = `Bearer ${userName}:admin`;
      return;
    }
    this._token = `Bearer ${userName}:user`;
  }

  logOut() {
    this._token = undefined;
  }
}
