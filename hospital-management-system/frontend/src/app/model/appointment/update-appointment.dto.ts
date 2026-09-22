import { AppointmentStatus } from "../../enum/appointment-status";

export interface UpdateAppointmentDto {
        appointmentDate:string;
        appointmentTime:string;
        appointmentStatus:AppointmentStatus
        reason:string;    
}
