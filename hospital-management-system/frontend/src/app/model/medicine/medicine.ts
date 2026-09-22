import { PrescriptionEntity } from "../prescription/prescription";

export interface MedicineEntity {
    id?:number;
    name:string;
    dosage:string;
    frequency:string;
    duration:string;
    notes:string;
    prescription?:PrescriptionEntity;
}
