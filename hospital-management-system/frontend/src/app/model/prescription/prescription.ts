import { AppointmentEntity } from "../appointment/appointment";
import { MedicineEntity } from "../medicine/medicine";

export interface PrescriptionEntity {
    id?:number;
    diagnosis:string;
    notes:string;
    prescriptionDate:string;
    appointment:AppointmentEntity;
    medicine:MedicineEntity[];
    
}
