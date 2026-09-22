import { AppointmentStatus } from "../../enum/appointment-status";
import { BillEntity } from "../bill/bill";
import { DoctorEntity } from "../doctor/doctor";
import { PatientEntity } from "../patient/patient-entity";
import { PrescriptionEntity } from "../prescription/prescription";

export interface AppointmentEntity {
    id?:number;
    appointmentDate:string;
    appointmentTime:string;
    appointmentStatus:AppointmentStatus
    reason:string;
    doctor:DoctorEntity;
    patient:PatientEntity;
    prescription?:PrescriptionEntity;
    bill?:BillEntity;
    registeredAt?:Date;

}
