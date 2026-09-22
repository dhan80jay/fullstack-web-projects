import { PaymentStatus } from "../../enum/payment-status";
import { AppointmentEntity } from "../appointment/appointment";

export interface BillEntity {
    id?:number;
    consultationFee:number;
    medicineCharges:number;
    otherCharges:number;
    totalAmount:number;
    paymentStatus:PaymentStatus;
    appointment:AppointmentEntity;
    billingDate:string;
}
