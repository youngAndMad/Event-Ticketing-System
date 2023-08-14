import { EventType } from './EventType';

export interface Event {
  id: number;
  title: string;
  date: string;
  description: string;
  type: EventType;
}
