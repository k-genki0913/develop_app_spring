package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Quiz;
import com.example.demo.form.QuizForm;
import com.example.demo.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/** Quizコントローラ */
@Controller
@RequestMapping("/quiz")
public class QuizController {

    /** DI対象 */
    @Autowired
    QuizService service;

    /** form-backing beanの初期化 */
    @ModelAttribute
    public QuizForm setUpForm() {
        QuizForm form = new QuizForm();
        form.setAnswer(true);
        return form;
    }

    /** Quizの一覧を表示する */
    @GetMapping
    public String showList(QuizForm quizForm, Model model) {
        // 新規登録設定
        quizForm.setNewQuiz(true);
        // 掲示板の一覧を取得する
        Iterable<Quiz> list = service.selectAll();
        // 表示用Modelへの格納
        model.addAttribute("list", list);
        model.addAttribute("title", "登録用フォーム");
        return "crud";
    }

    /** Quizデータを1件挿入 */
    @PostMapping("/insert")
    public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
            Model model, RedirectAttributes redirectAttributes) {
        // FormからEntityへの詰め替え
        Quiz quiz = new Quiz();
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());

        if (bindingResult.hasErrors()) {
            return showList(quizForm, model);
        }
        service.insertQuiz(quiz);
        redirectAttributes.addFlashAttribute("complete", "登録が完了しました");
        return "redirect:/quiz";
    }

    /** Quizデータを1件取得し、フォーム内に表示する */
    @GetMapping("/{id}")
    public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
        // Quizを取得
        Optional<Quiz> quizOpt = service.selectOneById(id);
        Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
        if (quizFormOpt.isPresent()) {
            quizForm = quizFormOpt.get();
        }
        makeUpdateModel(quizForm, model);
        return "crud";
    }

    /** 更新用のModelを作成する */
    private void makeUpdateModel(QuizForm quizForm, Model model) {
        model.addAttribute("id", quizForm.getId());
        quizForm.setNewQuiz(false);
        model.addAttribute("quizForm", quizForm);
        model.addAttribute("title", "更新用フォーム");
    }

    /** IDをKeyにしてデータを更新する */
    @PostMapping("/update")
    public String update(@Validated QuizForm quizForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        Quiz quiz = makeQuiz(quizForm);

        if (bindingResult.hasErrors()) {
            makeUpdateModel(quizForm, model);
            return "crud";
        }
        service.updateQuiz(quiz);
        redirectAttributes.addFlashAttribute("complete", "更新が完了しました");
        return "redirect:/quiz/" + quiz.getId();
    }

    /** IDをKeyにしてデータを削除する */
    @PostMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
        service.deleteQuizById(Integer.parseInt(id));
        redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました。");
        return "redirect:/quiz";
    }

    /** Quizデータをランダムで1件取得し、画面に表示する */
    @GetMapping("/play")
    public String showQuiz(QuizForm quizForm, Model model) {
        Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
        if (quizOpt.isPresent()) {
            Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
            quizForm = quizFormOpt.get();
        } else {
            model.addAttribute("msg", "問題がありません。。。");
            return "play";
        }

        model.addAttribute("quizForm", quizForm);
        return "play";
    }

    @PostMapping("/check")
    public String checkQuiz(QuizForm quizForm, @RequestParam Boolean answer, Model model) {
        if (service.checkQuiz(quizForm.getId(), answer)) {
            model.addAttribute("msg", "正解です!");
        } else {
            model.addAttribute("msg", "不正解です。。。");
        }

        return "answer";
    }

    /** QUizFormからQuizに詰め直して戻り値として返す */
    private Quiz makeQuiz(QuizForm quizForm) {
        Quiz quiz = new Quiz();
        quiz.setId(quizForm.getId());
        quiz.setQuestion(quizForm.getQuestion());
        quiz.setAnswer(quizForm.getAnswer());
        quiz.setAuthor(quizForm.getAuthor());
        return quiz;
    }

    /** QuizからQuizFormに詰め直して戻り値として返す */
    private QuizForm makeQuizForm(Quiz quiz) {
        QuizForm form = new QuizForm();
        form.setId(quiz.getId());
        form.setQuestion(quiz.getQuestion());
        form.setAnswer(quiz.getAnswer());
        form.setAuthor(quiz.getAuthor());
        form.setNewQuiz(false);
        return form;
    }

}
