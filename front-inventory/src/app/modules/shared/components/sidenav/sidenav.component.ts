import { MediaMatcher } from '@angular/cdk/layout';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.css'
})
export class SidenavComponent{

  mobileQuery?: MediaQueryList;

  menuNav = [
    {name : "Home", route : "home", icon : "home"},
    {name : "Categorías", route : "category", icon : "category"},
    {name : "Productos", route : "product", icon : "shopping"}
  ]

  contructor(media: MediaMatcher){
    this.mobileQuery = media.matchMedia('(max-witdh: 600px)');
  }

  shouldRun = true;
}
