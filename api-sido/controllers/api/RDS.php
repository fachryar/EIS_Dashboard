<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class RDS extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('RDS_model');
	}

	//TOTAL ANGGARAN USULAN SKPD
	public function apbd_post() {

		$tahun = $this->post("tahun");
		$skpd = $this->post("skpd");
		$anggaranskpd = $this->RDS_model->getAPBD($tahun, $skpd);
		
		if ($anggaranskpd) {
			$this->response([
	            'status' => true,
	            'anggaranskpd' => $anggaranskpd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function listskpd_get() {

		$tahun = $this->get("tahun");
		$anggaranskpd = $this->RDS_model->getSKPD($tahun);
		
		if ($anggaranskpd) {
			$this->response([
	            'status' => true,
	            'anggaranskpd' => $anggaranskpd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function programskpd_get() {

		$tahun = $this->get("tahun");
		$id_skpd = $this->get("id_skpd");
		$anggaranskpd = $this->RDS_model->getProgramSKPD($tahun, $id_skpd);
		
		if ($anggaranskpd) {
			$this->response([
	            'status' => true,
	            'anggaranskpd' => $anggaranskpd
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

}