<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Kependudukan extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('Kependudukan_model');
	}

	public function totalpenduduk_get(){
		$kependudukan = $this->Kependudukan_model->getJumlahPenduduk();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//By Gender
	public function bygender_get(){
		$kependudukan = $this->Kependudukan_model->getByGender();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function bygenderdetail_get(){
		$kependudukan = $this->Kependudukan_model->getByGenderDetail();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function bypendidikan_get(){
		$kependudukan = $this->Kependudukan_model->getByPendidikan();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function bypendidikandetail_get(){
		$kependudukan = $this->Kependudukan_model->getByPendidikanDetail();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function bypekerjaan_get(){
		$kependudukan = $this->Kependudukan_model->getByPekerjaan();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function byagama_get(){
		$kependudukan = $this->Kependudukan_model->getByAgama();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function allkecamatan_get(){
		$kependudukan = $this->Kependudukan_model->getKecamatan();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function byagamadetail_get(){
		$kecamatan = $this->input->get('kecamatan');
		$kependudukan = $this->Kependudukan_model->getByAgamaDetail($kecamatan);
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	public function byusia_get(){
		$kependudukan = $this->Kependudukan_model->getByUsia();
		
		if ($kependudukan) {
			$this->response([
	            'status' => true,
	            'kependudukan' => $kependudukan
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'Data not found!'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}
}