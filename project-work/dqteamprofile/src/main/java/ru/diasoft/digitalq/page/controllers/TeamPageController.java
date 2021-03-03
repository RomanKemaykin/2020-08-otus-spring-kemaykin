package ru.diasoft.digitalq.page.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TeamPageController {

    @GetMapping("/team")
    public String teamListPage(Model model) {
        return "teamList";
    }

    @GetMapping("/team/{id}/team-member")
    public String teamMemberListPage(Model model) {
        return "teamMemberList";
    }

    @GetMapping("/team/edit")
    public String editTeamGetPage(@RequestParam("id") String id, Model model) {
        return "teamEdit";
    }

    @GetMapping("/team/delete")
    public String deleteTeamGetPage(@RequestParam("id") String id, Model model) {
        return "teamDelete";
    }

    @GetMapping("/team/add")
    public String addTeamGetPage(Model model) {
        return "teamAdd";
    }
}
