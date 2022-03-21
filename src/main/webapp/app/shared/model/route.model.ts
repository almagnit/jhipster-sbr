export interface IRoute {
  id?: number;
  ort?: string | null;
  entfernung?: string | null;
  haltegrund?: string | null;
  gleis?: string | null;
  ausstieg?: string | null;
  gps?: string | null;
  ansage?: string | null;
  endAnsage?: string | null;
  startAnsage?: string | null;
  viaAnsage?: string | null;
  stopAnsage?: string | null;
  stopAnsageMode?: string | null;
  zugziel?: string | null;
  entwerter1?: string | null;
  entwerter2?: string | null;
  zoneninfo?: string | null;
  automat10?: string | null;
  attribute?: string | null;
  sprache1?: string | null;
  sprache2?: string | null;
  sprache3?: string | null;
  kurs?: string | null;
  zieltexte?: string | null;
  liniennummer?: string | null;
  gattung?: string | null;
  lineMarker?: string | null;
  ds010?: string | null;
  linienansage?: string | null;
  spurkranz?: string | null;
  skDauer?: string | null;
  skBoogie?: string | null;
  prmTur?: string | null;
  tursperrung?: string | null;
}

export const defaultValue: Readonly<IRoute> = {};
