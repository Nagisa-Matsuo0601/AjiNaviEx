package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Review;
import com.example.demo.form.ReviewEditForm;
import com.example.demo.service.EditService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EditController {

	private final EditService service;
	
	/*--- レビュー編集画面表示リクエスト ---*/
	@PostMapping("/show-edit-form")
	public String showEditForm(@ModelAttribute ReviewEditForm form) {
		return "edit-review";
	}

	/*--- レビュー更新リクエスト（編集画面より） ---*/
	@PostMapping("/edit-review")
	public String editReview(
			@Validated @ModelAttribute ReviewEditForm form,
			BindingResult result) {

		// 入力エラーがある場合には レビュー編集画面に戻す
		if (result.hasErrors()) {
			return "edit-review";
		}
		
		// 正常な場合に レビュー編集確認画面に 遷移する
		return "confirm-edit-review";
	}

	/*--- レビュー更新リクエスト（編集確認画面より） ---*/
	@PostMapping("/confirm-edit-review")
	public String confirmEditReview(
			@Validated ReviewEditForm form,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		// 入力エラーがある場合には レビュー編集画面に戻す
		if (result.hasErrors()) {
			return "edit-review";
		}

		Review r = new Review();
		r.setReviewId(form.getReviewId());
		r.setRestaurantId(form.getRestaurantId());
		r.setUserId(form.getUserId());
		r.setVisitDate(form.getVisitDate());
		r.setRating(form.getRating());
		r.setComment(form.getComment());
		
		service.edit(r);
		
		redirectAttributes.addFlashAttribute("msg", "(レビュー更新)");
		
		return "redirect:/complete";
	}

}
