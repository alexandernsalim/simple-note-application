<template>
  <b-card
    class="mb-2"
  >
    <template #header>
      <b-row>
        <b-col cols="10">
          <h5 class="text-left">{{note.title}}</h5>
        </b-col>
        <b-col cols="2">
          <b-button-group>
            <b-button variant="danger" size="sm" @click="deleteNote">
              <b-icon icon="trash"></b-icon>
            </b-button>
            <b-button variant="primary" size="sm" @click="showModal">
              <b-icon icon="pencil-square"></b-icon>
            </b-button>
          </b-button-group>
        </b-col>
      </b-row>
      <b-modal :id="modalId" centered title="Update note" hide-footer>
        <b-container fluid>
          <b-row>
            <b-col>
              <b-form @submit="updateNote">
                <b-form-group
                  label="Title"
                >
                  <b-form-input v-model="form.title" required></b-form-input>
                </b-form-group>
                <b-form-group
                  label="Description"
                >
                  <b-form-textarea rows="5" v-model="form.description" required></b-form-textarea>
                </b-form-group>
                <b-button-group>
                  <b-button variant="danger" @click="setInitialFormState">Reset</b-button>
                  <b-button type="submit" variant="primary">Update</b-button>
                </b-button-group>
              </b-form>
            </b-col>
          </b-row>
        </b-container>
      </b-modal>
    </template>
    <b-card-body>
      <b-card-text class="text-justify">
        {{note.description}}
      </b-card-text>
    </b-card-body>
  </b-card>
</template>

<script>
  import axios from 'axios';

  export default {
    name: "Note",
    props: {
      note: {},
    },
    data() {
      return {
        modalId: `modal-update-node-${this.note.id}`,
        form: {
          title: "",
          description: ""
        },
      }
    },
    methods: {
      setInitialFormState() {
        this.form.title = this.note.title;
        this.form.description = this.note.description;
      },
      showModal() {
        this.$bvModal.show(this.modalId);
      },
      updateNote(e) {
        e.preventDefault();
        const request = {
          title: this.form.title,
          description: this.form.description
        }
        axios.put(`/notes/${this.note.id}`, request)
          .then(() => {
            this.note.title = this.form.title;
            this.note.description = this.form.description;
            this.$bvModal.hide(this.modalId);
          })
          .catch((error) => {
            console.log(error);
          })
      },
      deleteNote() {
        axios.delete(`/notes/${this.note.id}`)
          .then(() => {
            this.$emit("deleted", this.note.id);
          })
          .catch((error) => {
            console.log(error);
          })
      }
    },
    created() {
      this.setInitialFormState();
    }
  }
</script>

<style scoped>

</style>