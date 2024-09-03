import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'limit'})
export class LimitPipe implements PipeTransform {
  transform(text: string, size: number): string {
    if (text.length <= size) {
      return text;
    }
    return `${text.substring(0, size)}...`;
  }
}
