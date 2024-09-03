import {Category} from "./category";
import {SustainabilityCriteria} from "./sustainabilityCriteria";

export interface Product {
  name: string,
  id?: number,
  price: number,
  description: string,
  labels: string[],
  sustainabilityCriteria: SustainabilityCriteria[],
  category: Category
}


