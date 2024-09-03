import {Component, OnDestroy, OnInit} from '@angular/core';
import {LogbookService} from "../../../services/logbook/logbook.service";
import {Subscription} from "rxjs";
import {LogbookEvent} from "../../../types/logbookEvent";
import {Action} from "../../../types/action";

@Component({
  selector: 'app-logbook-page',
  templateUrl: './logbook-page.component.html',
  styleUrls: ['./logbook-page.component.css']
})
export class LogbookPageComponent implements OnInit, OnDestroy {
  private _getLogbookSubscription!: Subscription;

  public logbookEvents: LogbookEvent[] = [];
  public columnsToDisplay = ['message', 'userName', "action", "dateTime"];

  constructor(private logbookService: LogbookService) {
  }

  getElementColor(action: string): string {
    switch (Action[action as keyof typeof Action]) {
      case Action.DELETE:
        return "#fc5e0a66";
      case Action.CREATE:
        return "#0bed4066";
      default:
        return "#429dff66";
    }
  }

  ngOnDestroy(): void {
    this._getLogbookSubscription.unsubscribe();
  }

  fetchLogbook() {
    this._getLogbookSubscription = this.logbookService.getLogbook().subscribe((data) => {
      this.logbookEvents = data;
    })
  }

  ngOnInit(): void {
    this.fetchLogbook()
  }
}
