import {HttpHeaders} from "@angular/common/http";

export default function standardHeaders(token: string) {
  let headers = new HttpHeaders();
  headers = headers.set('Content-Type', 'application/json; charset=utf-8');
  headers = headers.set('Authorization', `Bearer ${token}`);
  return headers
}
