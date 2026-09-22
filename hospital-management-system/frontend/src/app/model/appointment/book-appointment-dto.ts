import { DoctorEntity } from "../doctor/doctor";

export interface BookAppointmentDto {
    appointmentDate:string;
    appointmentTime:string;
    doctor:DoctorEntity;
    reason:string;
}
