import { AppointmentEntity } from "../appointment/appointment";
import { User } from "../user/user";

export interface DoctorEntity {
    id?:number;
    firstName:string;
    lastName:string;
    specialization:string;
    email:string;
    phoneNumber:string;
    qualification:string;
    experienceYears:number;
    user:User;
    appointments:AppointmentEntity[];

}
