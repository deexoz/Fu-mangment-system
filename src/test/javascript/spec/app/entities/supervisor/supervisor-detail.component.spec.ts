import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PumsTestModule } from '../../../test.module';
import { SupervisorDetailComponent } from 'app/entities/supervisor/supervisor-detail.component';
import { Supervisor } from 'app/shared/model/supervisor.model';

describe('Component Tests', () => {
  describe('Supervisor Management Detail Component', () => {
    let comp: SupervisorDetailComponent;
    let fixture: ComponentFixture<SupervisorDetailComponent>;
    const route = ({ data: of({ supervisor: new Supervisor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PumsTestModule],
        declarations: [SupervisorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SupervisorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupervisorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supervisor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supervisor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
