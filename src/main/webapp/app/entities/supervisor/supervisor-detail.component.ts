import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupervisor } from 'app/shared/model/supervisor.model';

@Component({
  selector: 'jhi-supervisor-detail',
  templateUrl: './supervisor-detail.component.html',
})
export class SupervisorDetailComponent implements OnInit {
  supervisor: ISupervisor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supervisor }) => (this.supervisor = supervisor));
  }

  previousState(): void {
    window.history.back();
  }
}
