export interface ISpecialInfo {
  id?: number;
  code?: string | null;
  item?: string | null;
  attribute?: string | null;
  shortTerminalDesc?: string | null;
  longTerminalDesc?: string | null;
  displayText?: string | null;
  ds003?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<ISpecialInfo> = {};
