import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Login } from './pages/login/login/login';
import { Admin } from './dashboard/admin/admin';
import { PatientDashboard } from './dashboard/patient/patientDashboard';
import { Patient } from './component/patient/patient';
import { AddPatient } from './forms/patient/add-patient/add-patient';
import { Doctor } from './component/doctor/doctor';
import { Appointment } from './component/appointment/appointment';
import { Bill } from './component/bill/bill';
import { Prescription } from './component/prescription/prescription';
import { AddAppointment } from './forms/appointment/add-appointment/add-appointment';
import { AddPrescription } from './forms/prescription/add-prescription/add-prescription';
import { AddDoctor } from './forms/doctor/add-doctor/add-doctor';
import { AddBilling } from './forms/bill/add-billing/add-billing';
import { ViewPatient } from './pages/patient/view-patient/view-patient';
import { ViewDoctor } from './pages/doctor/view-doctor/view-doctor';
import { ViewAppointment } from './pages/appointment/view-appointment/view-appointment';
import { ViewPrescription } from './pages/prescription/view-prescription/view-prescription';
import { ViewBill } from './pages/bill/view-bill/view-bill';
import { authGuard } from './guard/auth-guard';
import { UpdatePatient } from './forms/patient/update-patient/update-patient';
import { InnerViewAppointment } from './pages/appointment/innerAppointment/inner-view-appointment/inner-view-appointment';
import { UpdateDoctor } from './forms/doctor/updateDoctor/update-doctor/update-doctor';
import { AppointmentView } from './pages/doctor/appointmentView/appointment-view/appointment-view';
import { UpdateAppointment } from './forms/appointment/updateAppointment/update-appointment/update-appointment';
import { UpdatePrescription } from './forms/updatePrescription/update-prescription/update-prescription';
import { UpdateBill } from './forms/bill/updateBill/update-bill/update-bill';
import { roleGuard } from './guard/roleGuard/role-guard';
import { AccessDenied } from './pages/accessDenied/access-denied/access-denied';
import { Register } from './pages/registerUser/register/register';
import { RegisterDoctor } from './pages/registerUser/register-doctor/register-doctor';
import { RegisterPatient } from './pages/registerUser/register-patient/register-patient';
import { ForGotPassword } from './pages/forGotPassword/for-got-password/for-got-password';
import { BookAppointment } from './patientForms/appointment/book-appointment/book-appointment';
import { UpdatePatientByPatient } from './patientForms/update-patient/update-patient';
import { DoctorDashboard } from './dashboard/doctor/doctor-dashboard/doctor-dashboard';
import { PatientComponent } from './component/doctorComponent/patient-component/patient-component';
import { AppointmentComponent } from './component/doctorComponent/appointment-component/appointment-component';
import { PrescriptionComponent } from './component/doctorComponent/prescription-component/prescription-component';
import { ViewDoctorPatient } from './pages/doctorPages/view-doctor-patient/view-doctor-patient';
import { ViewDoctorAppointment } from './pages/doctorPages/view-doctor-appointment/view-doctor-appointment';
import { ViewDoctorPrescription } from './pages/doctorPages/view-doctor-prescription/view-doctor-prescription';
import { EditPrescription } from './forms/prescription/doctorPrescription/edit-prescription/edit-prescription';
import { AddDoctorPrescription } from './forms/prescription/doctorPrescription/add-doctor-prescription/add-doctor-prescription';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'forgot-password', component: ForGotPassword },
  { path: 'register', component: Register },
  { path: 'register/patient', component: RegisterPatient },
  { path: 'register/doctor', component: RegisterDoctor },

  { path: 'access-denied', component: AccessDenied },

  {
  path: 'dashboard/doctor',
  component: DoctorDashboard,
  canActivate: [authGuard, roleGuard],
  data: { role: 'ROLE_DOCTOR' },

  children: [

    {path:'patients',component:PatientComponent},
    {path:'appointments',component:AppointmentComponent},
    {path:'prescriptions',component:PrescriptionComponent}, 
    {
      path: 'view-patient/:patientId',
      component: ViewDoctorPatient
    },
    {
      path: 'view-appointment/:appointmentId',
      component: ViewDoctorAppointment
    },

    {
      path: 'create-prescription',
      component: AddDoctorPrescription
    },

    {
      path: 'view-prescription/:prescriptionId',
      component: ViewDoctorPrescription
    },

    {
      path: 'update-prescription/:id',
      component: EditPrescription
    }

  ]
},
  {
    path: 'dashboard/admin',
    component: Admin,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_ADMIN' },
    children: [
      // Component Routing
      { path: 'patient', component: Patient },
      { path: 'doctor', component: Doctor },
      { path: 'appointment', component: Appointment },
      { path: 'billing', component: Bill },
      { path: 'prescription', component: Prescription },

      // Add Forms Routing
      { path: 'add-appointment', component: AddAppointment },
      { path: 'create-prescription', component: AddPrescription },
      { path: 'add-doctor', component: AddDoctor },
      { path: 'add-bill', component: AddBilling },
      { path: 'add-patient', component: AddPatient },

      // View Each Record Routing
      { path: 'view-patient/:patientId', component: ViewPatient },
      {
        path: 'view-patient/:patientId/view-appointment/:appointmentId',
        component: InnerViewAppointment,
      },
      {
        path: 'view-patient/:patientId/view-appointment/:appointmentId/update-appointment',
        component: UpdateAppointment,
      },
      { path: 'view-doctor/:doctorId', component: ViewDoctor },
      { path: 'view-doctor/:doctorId/view-appointment/:appointmentId', component: AppointmentView },
      {
        path: 'view-doctor/:doctorId/view-appointment/:appointmentId/update-appointment',
        component: UpdateAppointment,
      },
      { path: 'view-appointment/:appointmentId', component: ViewAppointment },
      { path: 'update-appointment/:appointmentId', component: UpdateAppointment },
      { path: 'view-prescription/:prescriptionId', component: ViewPrescription },
      { path: 'view-bill/:id', component: ViewBill },

      //Edit
      { path: 'update-patient/:id', component: UpdatePatient },
      { path: 'update-doctor/:id', component: UpdateDoctor },
      { path: 'update-prescription/:id', component: UpdatePrescription },
      { path: 'update-bill/:id', component: UpdateBill },
    ],
  },
  {
    path: 'dashboard/patient',
    component: PatientDashboard,
    canActivate: [authGuard, roleGuard],
    data: { role: 'ROLE_PATIENT' },
    children: [
      { path: 'book-appointment', component: BookAppointment },
      { path: 'update-patient/:patientId', component: UpdatePatientByPatient },
    ],
  },
];
