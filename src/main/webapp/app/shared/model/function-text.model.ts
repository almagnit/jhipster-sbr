export interface IFunctionText {
  id?: number;
  code?: string | null;
  text?: string | null;
  language?: string | null;
}

export const defaultValue: Readonly<IFunctionText> = {};
