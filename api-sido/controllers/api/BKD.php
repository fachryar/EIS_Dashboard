<?php
use Restserver\Libraries\REST_Controller;
defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';


class BKD extends REST_Controller {

	public function __construct(){
		parent::__construct();
		$this->load->model('BKD_model');
	}

	public function allskpd_get() {
		$pegawai = $this->BKD_model->getAllSkpd();
		
		if ($pegawai) {
			$this->response([
	            'status' => true,
	            'pegawai' => $pegawai
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//SEARCH PEGAWAI BERDASARKAN NAMA
	public function searchpegawai_post() {

		$nama = $this->post("nama");
		$golongan = $this->post("golongan");
		$skpd = $this->post("skpd");
		$nip = $this->post("nip");
		$pegawai = $this->BKD_model->searchPegawai($nama, $golongan, $skpd, $nip);
		
		if ($pegawai) {
			$this->response([
	            'status' => true,
	            'pegawai' => $pegawai
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//GET JUMLAH PEGAWAI BERDASARKAN JENIS KELAMIN
	public function countbyjeniskelamin_get() {
		$pegawai = $this->BKD_model->countPegawaiByKel();
		
		if ($pegawai) {
			$this->response([
	            'status' => true,
	            'pegawai' => $pegawai
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//GET JUMLAH PEGAWAI BERDASARKAN USIA
	public function countbyusia_get() {
		$pegawai = $this->BKD_model->countPegawaiByUsia();
		
		if ($pegawai) {
			$this->response([
	            'status' => true,
	            'pegawai' => $pegawai
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}

	//GET JUMLAH PEGAWAI BERDASARKAN GOLONGAN
	public function countbygolkel_get() {
		$gol = $this->get("gol");
		$pegawai = $this->BKD_model->countPegawaiByGolKel($gol);
					
		if ($pegawai) {
			$this->response([
	            'status' => true,
	            'pegawai' => $pegawai
			], REST_Controller::HTTP_OK);
		} else {
			$this->response([
	            'status' => false,
	            'message' => 'data tidak ditemukan'
			], REST_Controller::HTTP_NOT_FOUND);
		}
	}
}