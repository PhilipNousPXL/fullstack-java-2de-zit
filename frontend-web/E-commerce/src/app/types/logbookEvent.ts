import {Action} from "./action";

export interface LogbookEvent {
  userName: string;
  dateTime: Date;
  action: Action;
  message: String;
}

