import { AppointmentEntity } from "../appointment/appointment";
import { User } from "../user/user";


export interface PatientEntity {
    id?:number;
    firstName:string;
    lastName:string;
    dateOfBirth:string;
    gender:string;
    bloodGroup:string;
    phoneNumber:string;
    email:string;
    address?:string;
    registeredAt?:Date;
    user:User;
    appointments?:AppointmentEntity[];
}
