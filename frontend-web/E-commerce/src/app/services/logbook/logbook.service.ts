import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationService} from "../authentication/authentication.service";
import {environment} from "../../../environments/environment";
import {map, Observable} from "rxjs";
import {LogbookEvent} from "../../types/logbookEvent";
import standardHeaders from "../header";

@Injectable({
  providedIn: 'root'
})
export class LogbookService {

  constructor(private http: HttpClient, private authService: AuthenticationService) {
  }

  getLogbook(): Observable<LogbookEvent[]> {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.get<LogbookEvent[]>(`${environment.backendUrl}/logbook`, {headers: headers})
      .pipe(map(data => data as LogbookEvent[]));
  }
}
