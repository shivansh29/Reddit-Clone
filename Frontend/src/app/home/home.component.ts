import { Component, OnInit } from '@angular/core';
import { PostModel } from '../shared/post.model';
import { PostService } from '../shared/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private postService: PostService) { 
    console.log("First Step");
    this.postService.getAllPosts().subscribe( post =>{
      this.posts = post;
    }
    )
  }

  posts: Array<PostModel> = [];
  
  ngOnInit(): void {
  }

  goToPost(id){

  }

}
